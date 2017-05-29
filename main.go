package main

import (
	"database/sql"
	"fmt"
	"taap_project/helpers"
	"taap_project/routes"

	"github.com/go-gorp/gorp"
	_ "github.com/go-sql-driver/mysql"
	gmux "github.com/gorilla/mux"
	"github.com/urfave/negroni"
)

var db *sql.DB
var dbmap *gorp.DbMap

func main() {
	db, _ = sql.Open("mysql", "root:#54nFr4nc15c0@/taap")

	helpers.InitDatabase()

	mux := gmux.NewRouter()
	routes.AddDrillRoutes(mux)
	routes.AddUserRoutes(mux)

	n := negroni.Classic()
	n.Use(negroni.HandlerFunc(helpers.VerifyMySQLConnection))
	n.UseHandler(mux)
	fmt.Println("Running...")
	n.Run(":8181")
}
