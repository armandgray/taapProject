package controllers

import (
	"net/http"
	"taap_project/helpers"
)

func NewDrillController(w http.ResponseWriter, r *http.Request) {
	if r.FormValue("submit") != "" {
		w.Write([]byte("working"))
		helpers.GetDatabase()
	}
}
