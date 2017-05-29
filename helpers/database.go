package helpers

import (
	"taap_project/models"

	"database/sql"
	"github.com/go-gorp/gorp"
	_ "github.com/go-sql-driver/mysql"
)

var db *sql.DB
var dbmap *gorp.DbMap

func InitDatabase() {
	db, _ = sql.Open("mysql", "root:#54nFr4nc15c0@/taap")
	dbmap = &gorp.DbMap{Db: db, Dialect: gorp.MySQLDialect{}}

	dbmap.AddTable(models.Drill{}).SetKeys(false, "Title")
}
