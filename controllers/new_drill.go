package controllers

import (
	"net/http"
	"taap_project/helpers"
	"taap_project/models"
)

func NewDrillController(w http.ResponseWriter, r *http.Request) {
	if r.FormValue("submit") != "" {
		dbmap := helpers.GetGorpMap()
		drill := models.Drill{"test", "test", 0}
		if err := dbmap.Insert(&drill); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
	}
}
