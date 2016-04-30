package com.spider.processor.util

import java.io.FileOutputStream

import com.spider.model.processor.OAContent
import org.apache.poi.hssf.usermodel.{HSSFCell, HSSFRow, HSSFWorkbook}

/**
  * Created by jason on 2016/4/29.
  */
class ExportExcel {
  def exportExcel(oAContents: List[OAContent]) = {
    val hssfworkbook = new HSSFWorkbook()
    val hssfsheet = hssfworkbook.createSheet()
    val hssfrow: HSSFRow = hssfsheet.createRow(0)
    hssfrow.createCell(0).setCellValue("所属市场")
    hssfrow.createCell(1).setCellValue("办事处")
    hssfrow.createCell(2).setCellValue("申请日期")
    hssfrow.createCell(3).setCellValue("专柜名称")
    hssfrow.createCell(4).setCellValue("促销类别")
    hssfrow.createCell(5).setCellValue("促销期间从")
    hssfrow.createCell(6).setCellValue("促销期间到")
    hssfrow.createCell(7).setCellValue("促销方案")
    hssfrow.createCell(8).setCellValue("竞品促销活动情况")
    var count = 1
    oAContents.foreach(oAContent => {
      val hssfrow: HSSFRow = hssfsheet.createRow(count)
      hssfrow.createCell(0).setCellValue(oAContent.market)
      hssfrow.createCell(1).setCellValue(oAContent.office)
      hssfrow.createCell(2).setCellValue(oAContent.applyDate)
      hssfrow.createCell(3).setCellValue(oAContent.specialName)
      hssfrow.createCell(4).setCellValue(oAContent.promotionsType)
      hssfrow.createCell(5).setCellValue(oAContent.beginDate)
      hssfrow.createCell(6).setCellValue(oAContent.endDate)
      hssfrow.createCell(7).setCellValue(oAContent.plan)
      hssfrow.createCell(8).setCellValue(oAContent.situation)
      count += 1
    })
    val fileoutputstream: FileOutputStream = new FileOutputStream("d:\\exceltext.xls")
    hssfworkbook.write(fileoutputstream)
    fileoutputstream.close()
  }
}
