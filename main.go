package main

import (
	"fmt"
	"taap_project/routes"

	_ "github.com/go-sql-driver/mysql"
	gmux "github.com/gorilla/mux"
	"github.com/urfave/negroni"
)

func main() {
	mux := gmux.NewRouter()
	mux = mux.PathPrefix("/taap/api").Subrouter()
	routes.AddDrillRoutes(mux)
	routes.AddUserRoutes(mux)

	n := negroni.Classic()
	n.UseHandler(mux)
	fmt.Println("Running...")
	n.Run(":8181")
}
