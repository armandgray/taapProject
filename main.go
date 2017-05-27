package main

import (
	"database/sql"
	"fmt"
	"net/http"
	"taap_project/routes"

	_ "github.com/go-sql-driver/mysql"
	gmux "github.com/gorilla/mux"
	"github.com/urfave/negroni"
)

var db *sql.DB

func main() {
	db, _ = sql.Open("mysql", "root:#54nFr4nc15c0@/taap")

	mux := gmux.NewRouter()
	routes.AddDrillRoutes(mux)
	routes.AddUserRoutes(mux)

	n := negroni.Classic()
	n.Use(negroni.HandlerFunc(VerifyMySQLConnection))
	n.UseHandler(mux)
	fmt.Println("Running...")
	n.Run(":8181")
}

func VerifyMySQLConnection(w http.ResponseWriter, r *http.Request, next http.HandlerFunc) {
	if err := db.Ping(); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	next(w, r)
}
