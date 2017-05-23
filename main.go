package main

import (
  "taap_project/routes"
  "fmt"

  "github.com/urfave/negroni"
  gmux "github.com/gorilla/mux"
  _ "github.com/go-sql-driver/mysql"
)

func main()  {
  mux := gmux.NewRouter()
  routes.NewDrillRoute(mux)

  n := negroni.Classic()
  n.UseHandler(mux)
  fmt.Println("Running...")
  n.Run(":8181")
}
