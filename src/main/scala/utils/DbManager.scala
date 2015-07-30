package utils

import java.sql.{Connection, DriverManager, ResultSet}
;

object DbManager {
  val conStrTemplate = "jdbc:mysql://localhost:3306/%s?user=root" // &password=yuvik2004"
  classOf[com.mysql.jdbc.Driver]


  def read (dbSchema: String, tableName: String): ResultSet = {
    val conn: Connection = connect(dbSchema)
    try {
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

      // Execute Query
      return statement executeQuery("SELECT * FROM %s" format tableName)
    }
    finally {
      conn close
    }
  }

  def selectSingleCell (dbSchema: String, query: String, columnName: String): Int = {
    val conn: Connection = connect(dbSchema)
    var distance: Int = 0
    try {
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      // Execute Query
      val rs: ResultSet = statement executeQuery query
      while (rs.next()){
        distance = rs getInt columnName
      }
    }
    finally {
      conn close
    }
    return distance
  }

  def write(dbSchema: String,statementTemplate: String, commaSeparatedValues: Array[String]) {
    val conn: Connection = connect(dbSchema)
    conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)
    try {
      for (i <- 0 to commaSeparatedValues.length) {
        val query: String = "%s VALUES (%s)" format (statementTemplate, commaSeparatedValues(i))

        val prep = conn.prepareStatement(query)
        prep.executeUpdate
      }
    } finally {
      conn close
    }
  }

  def delete(dbSchema: String, statement: String) {
    val conn: Connection = connect(dbSchema)
    conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)
    try {
      conn prepareStatement(statement) executeUpdate
    } finally {
      conn close
    }
  }

  def connect(dbSchema: String): Connection = {
    val conn_str: String = conStrTemplate format (dbSchema)
    val conn = DriverManager.getConnection(conn_str)
    conn
  }
}
