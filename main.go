package main

import (
  "fmt"
  "net/http"

  "github.com/urfave/negroni"
  _ "github.com/go-sql-driver/mysql"
)

func main()  {
	apiUrl := "/taap/api"

  mux := http.NewServeMux()
  mux.HandleFunc(apiUrl + "/", func(w http.ResponseWriter, r *http.Request) {
  	w.Write([]byte("working"))
  	})

  n := negroni.Classic()
  n.UseHandler(mux)
  fmt.Println("Running...")
  n.Run(":8181")
}
