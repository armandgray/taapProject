package main

import (
  "taap_project/controllers"
  "fmt"

  "github.com/urfave/negroni"
  gmux "github.com/gorilla/mux"
  _ "github.com/go-sql-driver/mysql"
)

func main()  {
  apiUrl := "/taap/api"

  mux := gmux.NewRouter()
  mux.HandleFunc(apiUrl + "/drills/new", controllers.NewDrillController).Methods("POST")

  n := negroni.Classic()
  n.UseHandler(mux)
  fmt.Println("Running...")
  n.Run(":8181")
}
