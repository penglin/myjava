/*

JAVAʵ��ƴͼ��Ϸ

www.firnow.com    ʱ�� �� 2008-12-18  ���ߣ�����   �༭���Ի� �����  1709 [ ���� ]
-
-
�ۺ� ��Դ ������ ����*/


package swing;



/**
 * <p>Title: LoonFramework</p>
 * <p>Description: ƴͼͼ����[δ�Ż�]���Ż��㷨��������loonframework-game����С���</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: LoonFramework</p>
 * @author chenpeng  
 * @email��ceponline@yahoo.com.cn 
 * @version 0.1
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.loon.framework.helper.ImageHelper;

public class BlockImage extends Canvas {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Image _img;

    private Image _img2;

    private Graphics bg;

    private Image backimage;

    private int blocks[];

    private boolean isEvent;

    private MediaTracker mt;

    private int _width;

    private int _height;

    private int _RS;

    private int _CS;

    private Image screen = null;

    private Graphics later = null;

    private int _objWidth;

    private int _objHeight;

    private int _COUNT;

    /**
     * �����������ڲ�����init������
     * 
     * @param bImage
     * @param overImage
     * @param cs
     * @param rs
     */
    public BlockImage(Image bImage, Image overImage, int cs, int rs) {
        init(bImage, overImage, cs, rs);
    }
    
    /**
     * ��ʼ��ƴͼ������
     * 
     * @param bImage
     * @param overImage
     * @param cs
     * @param rs
     */
    public void init(Image bImage, Image overImage, int cs, int rs) {
        // ����
        _CS = cs;
        // ����
        _RS = rs;
        // ����ƴͼ��ͼ��
        _img = bImage;

        // ���ʵ�ʴ������
        _width = _img.getWidth(null);
        // ���ʵ�ʴ���ߡ�
        _height = _img.getHeight(null);
        // ��õ���ͼ�����
        _objWidth = _width / _CS;
        // ��õ���ͼ��ߡ�
        _objHeight = _height / _RS;

        // ������ֱ��ʹ��backimage��һ��ͼ�����򻺳�ѡ�������ʵ�ʱ���ͼ���=ͼ�θ�+����ͼ��ߡ�
        backimage = new BufferedImage(_width, _height + _objHeight, 1);
        // ������ɵ�ͼ��
        later = backimage.getGraphics();
        // �ٴ���һ��ͼ��������Ϊͼ�񻺴��á�
        screen = new BufferedImage(_width, _height, 1);
        // ��û����ͼ��
        bg = screen.getGraphics();
        // ��õ�ͬͼƬ���������顣
        _COUNT = _CS * _RS;
        blocks = new int[_COUNT];
        // ��ʼ��Ϊ�ǵ����
        isEvent = false;
        // �������ƴͼ����ʾͼ��
        _img2 = overImage;
        // ��ʼ��ͼ�������
        for (int i = 0; i < _COUNT; i++) {
            blocks[i] = i;
        }
        // ����MediaTracker�����Ը���ͼ��״̬��
        mt = new MediaTracker(this);
        // ���ر����ٵ�ͼ��
        mt.addImage(_img, 0);
        mt.addImage(_img2, 0);
        // ͬ�����롣
        try {
            mt.waitForID(0);
        } catch (InterruptedException interruptedexception) {
            return;
        }
        // �������ͼ��������ݡ�
        rndPannel();

    } 
    
    /**
     * ��洰��ͼ��
     */
    public void paint(Graphics g) {
        // ���ͼ�����롣
        if (mt.checkID(0)) {
            // ���ײ㱳����
            bg.drawImage(backimage, 0, 0, null);
            // �ж��Ƿ񴥷�����¼���
            if (!isEvent) {
                // ���ñ���ɫ��
                bg.setColor(Color.black);
                // ѭ������СͼƬ�ڱ��������С�
                for (int i = 0; i < _CS; i++) {
                    for (int j = 0; j < _RS; j++)
                        bg.drawRect(i * _objWidth, j * _objHeight, _objWidth,
                                _objHeight);

                }

            }
            // ��������¼�����������ʤ��ͼƬʱ�����������ʾ��
            if (isEvent && _img2 != null) {
                bg.drawImage(_img2, 0, 0, null);
            }
        }
        // �ٷ�����ͼ��ʱ��Ӧ��ѭ��ʾͼ�������һ�εĻ���ԭ��һ���ԵĽ��������Ƶ����塣
        // ����˵��Ҳ���ǲ�ȡ[˫����]�ķ�ʽ�����и��Ӳ������ڻ�������ɣ�Ҳֻ���������ܱ�������ӳ���˸��
        g.drawImage(screen, 0, 0, this);
        g.dispose();
    }

    /**
     * ���ͼ��
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * ������¼���
     */
    public boolean mouseDown(Event event, int i, int j) {

        if (isEvent)
            return true;
        // ������λ����СͼƬ��
        int k = i / _objWidth;
        int l = j / _objHeight;
        copy(0, 0, 0, _RS);
        copy(k, l, 0, 0);
        copy(0, _RS, k, l);
        int i1 = blocks[0];
        // ����ѡ��ͼƬ�洢����
        blocks[0] = blocks[l * _CS + k];
        blocks[l * _CS + k] = i1;
        int j1;
        for (j1 = 0; j1 < _COUNT; j1++) {
            if (blocks[j1] != j1) {
                break;
            }
        }
        if (j1 == _COUNT)
            isEvent = true;
//        repaint();
        return true;
    }

    public boolean mouseUp(Event event, int i, int j) {
        return true;
    }

    public boolean mouseDrag(Event event, int i, int j) {
        return true;
    }

    /**
     * copy������ͼ������
     * 
     * @param i
     * @param j
     * @param k
     * @param l
     */
    void copy(int i, int j, int k, int l) {
        later.copyArea(i * _objWidth, j * _objHeight, _objWidth, _objHeight,
                (k - i) * _objWidth, (l - j) * _objHeight);
    }

    /**
     * �¼�����״̬��
     * @return
     */
    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean isEvent) {
        this.isEvent = isEvent;
    }

    /**
    * ����������ͼƬ��
     * 
     */
    void rndPannel() {
        later.drawImage(_img, 0, 0, this);
        for (int i = 0; i < (_COUNT * _CS); i++) {
            int j = (int) ((double) _CS * Math.random());
            int k = (int) ((double) _RS * Math.random());
            int l = (int) ((double) _CS * Math.random());
            int i1 = (int) ((double) _RS * Math.random());
            copy(j, k, 0, _RS);
            copy(l, i1, j, k);
            copy(0, _RS, l, i1);
            int j1 = blocks[k * _CS + j];
            blocks[k * _CS + j] = blocks[i1 * _CS + l];
            blocks[i1 * _CS + l] = j1;
        }

    }

    public static void main(String[] args) {

        /*Frame frm = new Frame("�򵥵�JAVAƴͼЧ��ʵ��[��Loonframework����ṩ]");
        frm.setSize(480, 500);
        frm.setResizable(false);
        *//**
         * PS:ImageHelper.loadImageΪ Loonframework�����helper�·�����Ϊ��������javax��չ����������
         * ��ʹ��ImageIO��ط������档
         *//*
        // ����ͼ��
        Image backImage = ImageHelper.loadImage("1.jpg", true);
        Image overImage = ImageHelper.loadImage("2.gif", true);
        // BlockImage�в����ֱ�Ϊ ���ڷֽ��ƴͼ����ɺ���ʾ���֣����ͼƬΪ�ּ��У��ֲ��ͼƬΪ���С�
        //����ʹ��������ͼƬ��Ϊ����ͼ��
        frm.add(new BlockImage(backImage, overImage, 4, 4));
        backImage = null;
        overImage = null;
        // ��ʾ���塣
        frm.setVisible(true);*/

    	JFrame frm = new JFrame("�򵥵�JAVAƴͼЧ��ʵ��[��Loonframework����ṩ]");
        frm.setSize(480, 500);
        frm.setResizable(false);
        /**
         * PS:ImageHelper.loadImageΪ Loonframework�����helper�·�����Ϊ��������javax��չ����������
         * ��ʹ��ImageIO��ط������档
         */
        // ����ͼ��
        Image backImage = ImageHelper.loadImage("1.jpg", true);
        Image overImage = ImageHelper.loadImage("2.gif", true);
        // BlockImage�в����ֱ�Ϊ ���ڷֽ��ƴͼ����ɺ���ʾ���֣����ͼƬΪ�ּ��У��ֲ��ͼƬΪ���С�
        //����ʹ��������ͼƬ��Ϊ����ͼ��
        frm.add(new BlockImage(backImage, overImage, 4, 4));
        backImage = null;
        overImage = null;
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ��ʾ���塣
        frm.setVisible(true);
    }

}