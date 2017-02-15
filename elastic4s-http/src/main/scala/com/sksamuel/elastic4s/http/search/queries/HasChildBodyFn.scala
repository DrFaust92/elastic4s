package com.sksamuel.elastic4s.http.search.queries

import com.sksamuel.elastic4s.searches.queries.HasChildQueryDefinition
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory}

object HasChildBodyFn {

  def apply(q: HasChildQueryDefinition): XContentBuilder = {
    val builder = XContentFactory.jsonBuilder()
    builder.startObject()
    builder.startObject("has_child")
    builder.field("type", q.`type`)
    q.minMaxChildren.foreach { minmax =>
      if (minmax._1 > 0)
        builder.field("min_children", minmax._1)
      if (minmax._2 > 0)
        builder.field("max_children", minmax._2)
    }
    builder.field("score_mode", q.scoreMode.name.toLowerCase)
    builder.rawField("query", QueryBuilderFn(q.query).bytes)
    q.ignoreUnmapped.foreach(builder.field("ignore_unmapped", _))
    q.boost.foreach(builder.field("boost", _))
    q.queryName.foreach(builder.field("_name", _))
    builder.endObject()
    builder.endObject()
    builder
  }
}