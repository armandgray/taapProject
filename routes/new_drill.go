package routes

import (
  "taap_project/controllers"

  gmux "github.com/gorilla/mux"
)

func NewDrillRoute(mux *gmux.Router) {
	apiUrl := "/taap/api"
  mux.HandleFunc(apiUrl + "/drills/new", controllers.NewDrillController).Methods("GET")
}