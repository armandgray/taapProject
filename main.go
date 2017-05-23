package main

import (
  "taap_project/controllers"
  "fmt"
  "net/http"

  "github.com/urfave/negroni"
  _ "github.com/go-sql-driver/mysql"
)

func main()  {
  apiUrl := "/taap/api"

  mux := http.NewServeMux()
  mux.HandleFunc(apiUrl + "/drills/new", controllers.NewDrillController)

  n := negroni.Classic()
  n.UseHandler(mux)
  fmt.Println("Running...")
  n.Run(":8181")
}
