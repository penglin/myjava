package adman;

import java.util.Date;

import cn.adsit.analytics.core.analysis2.scan.ScanInit;

public class T1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 得到当前时间毫秒数
		long currentTimeMillis = System.currentTimeMillis();
		// 得到前置扫描开始时间毫秒数
		long startScanTimeMillis = DateUtil.parseDateTime2MillSeconds(this.startScanTime);
		// 得到前置扫描结束时间毫秒数
		long finishScanTimeMillis = 0;
		if (this.finishScanTime != null)
			finishScanTimeMillis = DateUtil.parseDateTime2MillSeconds(this.finishScanTime);
		// 计算得到正常任务生成截止时间
		long genNormalScanCutoff = currentTimeMillis - this.normalScanPassInterval * 60 * 1000;
		if (finishScanTimeMillis > 0 && genNormalScanCutoff > finishScanTimeMillis)
			genNormalScanCutoff = finishScanTimeMillis;
		// 计算得到正常任务生成开始时间
		long genNormalScanStart = genNormalScanCutoff - this.scanTimeScope * 60 * 1000;
		if (genNormalScanStart < startScanTimeMillis)
			genNormalScanStart = startScanTimeMillis;
		// 生成正常任务
		this.genAnalysisScans(ScanInit.hostIp, AnalysisScan.SCAN_TYPE_NORMAL, DateUtil.formateTime(new Date(genNormalScanStart)), DateUtil
				.formateTime(new Date(genNormalScanCutoff)));
		// 计算得到延迟任务生成截止日期
		long genDelayScanCutoff = currentTimeMillis - this.delayScanPassInterval * 60 * 1000;
		if (finishScanTimeMillis > 0 && genDelayScanCutoff > finishScanTimeMillis)
			genDelayScanCutoff = finishScanTimeMillis;
		// 计算得到延迟任务生成开始时间
		long genDelayScanStart = genDelayScanCutoff - this.scanTimeScope * 60 * 1000;
		if (genDelayScanStart < startScanTimeMillis)
			genDelayScanStart = startScanTimeMillis;
		// 生成延迟任务
		this.genAnalysisScans(ScanInit.hostIp, AnalysisScan.SCAN_TYPE_DELAY, DateUtil.formateTime(new Date(genDelayScanStart)), DateUtil.formateTime(new Date(
				genDelayScanCutoff)));
	}

}
