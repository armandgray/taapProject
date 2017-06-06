package helpers

import (
	"taap_project/models"

	"database/sql"
	"github.com/go-gorp/gorp"
	_ "github.com/go-sql-driver/mysql"
	"net/http"
)

var db *sql.DB
var dbmap *gorp.DbMap

func GetDatabase() *sql.DB {
	return db
}

func GetGorpMap() *gorp.DbMap {
	return dbmap
}

func InitDatabase() {
	db, _ = sql.Open("mysql", "root:#54nFr4nc15c0@/taap")
	dbmap = &gorp.DbMap{Db: db, Dialect: gorp.MySQLDialect{}}

	dbmap.AddTable(models.Drill{}).SetKeys(false, "Title")
	dbmap.AddTable(models.User{}).SetKeys(false, "Number")
	dbmap.AddTable(models.SessionLog{}).SetKeys(false, "LogId")
}

func VerifyMySQLConnection(w http.ResponseWriter, r *http.Request, next http.HandlerFunc) {
	db, _ = sql.Open("mysql", "root:#54nFr4nc15c0@/taap")
	if err := db.Ping(); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	next(w, r)
}
