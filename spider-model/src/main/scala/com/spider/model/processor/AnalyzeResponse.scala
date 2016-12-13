package com.spider.model.processor

import com.spider.model.Content

/**
  * Created by jason on 16-5-18.
  */
@SerialVersionUID(1L)
class AnalyzeResponse(val spiderId: String, val step: Int, val targets: List[String], var contents: List[Content]) extends Serializable {


  override def toString = s"AnalyzeResponse($spiderId, $step, $targets, $contents)"
}
