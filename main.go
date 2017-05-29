package main

import (
	"fmt"
	"taap_project/helpers"
	"taap_project/routes"

	gmux "github.com/gorilla/mux"
	"github.com/urfave/negroni"
)

func main() {
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
