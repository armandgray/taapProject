package routes

import (
	"taap_project/controllers"

	gmux "github.com/gorilla/mux"
)

func AddUserRoutes(mux *gmux.Router) {
	mux = mux.PathPrefix("/taap/api").Subrouter()
	mux.HandleFunc("/users/new", controllers.NewDrillController).Methods("GET")
}
