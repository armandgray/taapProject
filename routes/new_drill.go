package routes

import (
	"taap_project/controllers"

	gmux "github.com/gorilla/mux"
)

func NewDrillRoute(mux *gmux.Router) {
	apiUrl := "/taap/api"
	mux.HandleFunc(apiUrl+"/drills/new", controllers.NewDrillController).Methods("GET")
}

func AddUserRoutes(mux *gmux.Router) {
	apiUrl := "/taap/api"
	mux.HandleFunc(apiUrl+"/users/new", controllers.NewDrillController).Methods("GET")
}
