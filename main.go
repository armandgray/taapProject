package main

import (
	"database/sql"
	"fmt"
	"net/http"
	"taap_project/routes"

	"github.com/go-gorp/gorp"
	_ "github.com/go-sql-driver/mysql"
	gmux "github.com/gorilla/mux"
	"github.com/urfave/negroni"
)

var db *sql.DB
var dbmap *gorp.DbMap

func main() {
	initDatabase()

	mux := gmux.NewRouter()
	routes.AddDrillRoutes(mux)
	routes.AddUserRoutes(mux)

	n := negroni.Classic()
	n.Use(negroni.HandlerFunc(VerifyMySQLConnection))
	n.UseHandler(mux)
	fmt.Println("Running...")
	n.Run(":8181")
}

func initDatabase() {
	db, _ = sql.Open("mysql", "root:#54nFr4nc15c0@/taap")
	dbmap = &gorp.DbMap{Db: db, Dialect: gorp.MySQLDialect{}}

}

func VerifyMySQLConnection(w http.ResponseWriter, r *http.Request, next http.HandlerFunc) {
	if err := db.Ping(); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	next(w, r)
}
