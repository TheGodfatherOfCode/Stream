package com.YunTu.LineStream.SimpleUVDriver.DateFat;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.YunTu.LineStream.SimpleUVDriver.DemoDriver;

public class DataSpliceFarmat {
	public static Logger LOG = LoggerFactory.getLogger(DateFmtBolt.class);
	
	/**
	 * 简单输出ID
	 * @param tuple
	 * @param IDNo
	 */
	public String sliceFormat(Tuple tuple,int IDNo) {
		String line = tuple.getString(0);
		String[] lines = line.split("\t", -1);
		if (lines != null && lines.length == 40) {
			String IDstr = lines[IDNo] ;
			return IDstr;
			/*if (IDstr.matches("-?\\d+")) {
			} else {
				LOG.error("数据第"+IDNo+"行ID不能转化为数字  \t  line" + line);
			}*/
		}else {
			LOG.error("数据tab分割不等于40 \t  line" + line);
			return  null;
		}
	}
	
	/**
	 * 
	 * @param tuple
	 * @param release_dateN
	 * @param hit_tagN
	 */
	public String sliceFormat(Tuple tuple,int release_dateN,int hit_tagN) {
		String line = tuple.getString(0);
		String[] lines = line.split("\t", -1);
		if (lines != null && lines.length == 40) {
			String release_date = lines[release_dateN]; // 35
			String hit_tag = lines[hit_tagN];// 33
			String[] daydates = release_date.split(" ", -1);
			if (daydates != null && daydates.length >= 2) {
				String[] dates = daydates[0].split("-", -1);
				if (dates != null && dates.length >= 3) {
					String year = dates[0];
					String mouth = dates[1];
					String day = dates[2];
					String[] hit_tags = hit_tag.split("/", -1);
					if (hit_tags.length>=4) {
						String tag = "";
						tag = hit_tags[3];
						/*if (hit_tag.indexOf("/小米") != -1) {
							tag = "XiaoMi";
						}*/
						String tags = 
								"DT01" + DemoDriver.segmentation 
								+ "DSSL" + DemoDriver.segmentation 
								+ tag + DemoDriver.segmentation 
								+ year + DemoDriver.segmentation 
								+ mouth + DemoDriver.segmentation 
								+ "0" + DemoDriver.segmentation// day
								+ "0" + DemoDriver.segmentation 
								+ "/eb/"/*dwtvs_type*/;// "/eb/"
						return tags;
					} else {
						LOG.error("hit_tag /分割不足4" + "\t  line" + line);
						return null;
					}
				} else {
					LOG.error("时间-分割不足3" + "\t  line" + line);
					return null;
				}
			} else {
				LOG.error("时间 分割不足2 \t  line" + "\t" + line);
				return null;
			}
		} else {
			LOG.error("数据tab分割不等于40 \t  line" + line);
			return null;
		}
	}

}
