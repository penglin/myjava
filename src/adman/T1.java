package adman;

import java.util.Date;

import cn.adsit.analytics.core.analysis2.scan.ScanInit;

public class T1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// �õ���ǰʱ�������
		long currentTimeMillis = System.currentTimeMillis();
		// �õ�ǰ��ɨ�迪ʼʱ�������
		long startScanTimeMillis = DateUtil.parseDateTime2MillSeconds(this.startScanTime);
		// �õ�ǰ��ɨ�����ʱ�������
		long finishScanTimeMillis = 0;
		if (this.finishScanTime != null)
			finishScanTimeMillis = DateUtil.parseDateTime2MillSeconds(this.finishScanTime);
		// ����õ������������ɽ�ֹʱ��
		long genNormalScanCutoff = currentTimeMillis - this.normalScanPassInterval * 60 * 1000;
		if (finishScanTimeMillis > 0 && genNormalScanCutoff > finishScanTimeMillis)
			genNormalScanCutoff = finishScanTimeMillis;
		// ����õ������������ɿ�ʼʱ��
		long genNormalScanStart = genNormalScanCutoff - this.scanTimeScope * 60 * 1000;
		if (genNormalScanStart < startScanTimeMillis)
			genNormalScanStart = startScanTimeMillis;
		// ������������
		this.genAnalysisScans(ScanInit.hostIp, AnalysisScan.SCAN_TYPE_NORMAL, DateUtil.formateTime(new Date(genNormalScanStart)), DateUtil
				.formateTime(new Date(genNormalScanCutoff)));
		// ����õ��ӳ��������ɽ�ֹ����
		long genDelayScanCutoff = currentTimeMillis - this.delayScanPassInterval * 60 * 1000;
		if (finishScanTimeMillis > 0 && genDelayScanCutoff > finishScanTimeMillis)
			genDelayScanCutoff = finishScanTimeMillis;
		// ����õ��ӳ��������ɿ�ʼʱ��
		long genDelayScanStart = genDelayScanCutoff - this.scanTimeScope * 60 * 1000;
		if (genDelayScanStart < startScanTimeMillis)
			genDelayScanStart = startScanTimeMillis;
		// �����ӳ�����
		this.genAnalysisScans(ScanInit.hostIp, AnalysisScan.SCAN_TYPE_DELAY, DateUtil.formateTime(new Date(genDelayScanStart)), DateUtil.formateTime(new Date(
				genDelayScanCutoff)));
	}

}