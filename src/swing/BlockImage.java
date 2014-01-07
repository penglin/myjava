/*

JAVA实现拼图游戏

www.firnow.com    时间 ： 2008-12-18  作者：佚名   编辑：辉辉 点击：  1709 [ 评论 ]
-
-
综合 资源 电子书 社区*/


package swing;



/**
 * <p>Title: LoonFramework</p>
 * <p>Description: 拼图图像处理[未优化]（优化算法已内置于loonframework-game框架中。）</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: LoonFramework</p>
 * @author chenpeng  
 * @email：ceponline@yahoo.com.cn 
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
     * 析构函数，内部调用init方法。
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
     * 初始化拼图参数。
     * 
     * @param bImage
     * @param overImage
     * @param cs
     * @param rs
     */
    public void init(Image bImage, Image overImage, int cs, int rs) {
        // 列数
        _CS = cs;
        // 行数
        _RS = rs;
        // 加载拼图用图像。
        _img = bImage;

        // 获得实际窗体宽。
        _width = _img.getWidth(null);
        // 获得实际窗体高。
        _height = _img.getHeight(null);
        // 获得单块图像宽。
        _objWidth = _width / _CS;
        // 获得单块图像高。
        _objHeight = _height / _RS;

        // 本程序直接使用backimage上一块图形区域缓冲选择项，所以实际背景图像高=图形高+额外图块高。
        backimage = new BufferedImage(_width, _height + _objHeight, 1);
        // 获得生成的图形
        later = backimage.getGraphics();
        // 再创建一块图像区域，作为图像缓存用。
        screen = new BufferedImage(_width, _height, 1);
        // 获得缓存的图形
        bg = screen.getGraphics();
        // 获得等同图片总数的数组。
        _COUNT = _CS * _RS;
        blocks = new int[_COUNT];
        // 初始化为非点击。
        isEvent = false;
        // 加载完成拼图的显示图。
        _img2 = overImage;
        // 初始化图块参数。
        for (int i = 0; i < _COUNT; i++) {
            blocks[i] = i;
        }
        // 载入MediaTracker，用以跟踪图像状态。
        mt = new MediaTracker(this);
        // 加载被跟踪的图像。
        mt.addImage(_img, 0);
        mt.addImage(_img2, 0);
        // 同步载入。
        try {
            mt.waitForID(0);
        } catch (InterruptedException interruptedexception) {
            return;
        }
        // 随机生成图像面板内容。
        rndPannel();

    } 
    
    /**
     * 描绘窗体图像。
     */
    public void paint(Graphics g) {
        // 检查图像载入。
        if (mt.checkID(0)) {
            // 描绘底层背景。
            bg.drawImage(backimage, 0, 0, null);
            // 判断是否触发完成事件。
            if (!isEvent) {
                // 设置背景色。
                bg.setColor(Color.black);
                // 循环绘制小图片于背景缓存中。
                for (int i = 0; i < _CS; i++) {
                    for (int j = 0; j < _RS; j++)
                        bg.drawRect(i * _objWidth, j * _objHeight, _objWidth,
                                _objHeight);

                }

            }
            // 仅当完成事件触发并且有胜利图片时，载入完成提示。
            if (isEvent && _img2 != null) {
                bg.drawImage(_img2, 0, 0, null);
            }
        }
        // 举凡绘制图像时，应遵循显示图像仅绘制一次的基本原则，一次性的将背景绘制到窗体。
        // 简单来说，也就是采取[双缓存]的方式，所有复杂操作皆在缓存区完成，也只有这样才能避免产生延迟闪烁。
        g.drawImage(screen, 0, 0, this);
        g.dispose();
    }

    /**
     * 变更图像。
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * 鼠标点击事件。
     */
    public boolean mouseDown(Event event, int i, int j) {

        if (isEvent)
            return true;
        // 换算点击位置与小图片。
        int k = i / _objWidth;
        int l = j / _objHeight;
        copy(0, 0, 0, _RS);
        copy(k, l, 0, 0);
        copy(0, _RS, k, l);
        int i1 = blocks[0];
        // 换算选中图片存储区。
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
     * copy换算后的图像区域。
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
     * 事件触发状态。
     * @return
     */
    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean isEvent) {
        this.isEvent = isEvent;
    }

    /**
    * 随机生成面板图片。
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

        /*Frame frm = new Frame("简单的JAVA拼图效果实现[由Loonframework框架提供]");
        frm.setSize(480, 500);
        frm.setResizable(false);
        *//**
         * PS:ImageHelper.loadImage为 Loonframework框架中helper下方法，为不依赖于javax扩展包而开发。
         * 可使用ImageIO相关方法代替。
         *//*
        // 加载图像。
        Image backImage = ImageHelper.loadImage("1.jpg", true);
        Image overImage = ImageHelper.loadImage("2.gif", true);
        // BlockImage中参数分别为 用于分解的拼图，完成后显示文字，拆分图片为分几列，分拆分图片为几行。
        //建议使用正方形图片作为背景图。
        frm.add(new BlockImage(backImage, overImage, 4, 4));
        backImage = null;
        overImage = null;
        // 显示窗体。
        frm.setVisible(true);*/

    	JFrame frm = new JFrame("简单的JAVA拼图效果实现[由Loonframework框架提供]");
        frm.setSize(480, 500);
        frm.setResizable(false);
        /**
         * PS:ImageHelper.loadImage为 Loonframework框架中helper下方法，为不依赖于javax扩展包而开发。
         * 可使用ImageIO相关方法代替。
         */
        // 加载图像。
        Image backImage = ImageHelper.loadImage("1.jpg", true);
        Image overImage = ImageHelper.loadImage("2.gif", true);
        // BlockImage中参数分别为 用于分解的拼图，完成后显示文字，拆分图片为分几列，分拆分图片为几行。
        //建议使用正方形图片作为背景图。
        frm.add(new BlockImage(backImage, overImage, 4, 4));
        backImage = null;
        overImage = null;
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 显示窗体。
        frm.setVisible(true);
    }

}
