package routes

import (
	"taap_project/controllers"

	gmux "github.com/gorilla/mux"
)

func AddDrillRoutes(mux *gmux.Router) {
	mux = mux.PathPrefix("/taap/api").Subrouter()
	mux.HandleFunc("/drills/all", controllers.AllDrillsController).Methods("GET")
	mux.HandleFunc("/drills/new", controllers.NewDrillController).Methods("GET")
	mux.HandleFunc("/drills", controllers.InputDrillController).Methods("GET")
}
