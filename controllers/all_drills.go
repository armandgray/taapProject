package controllers

import (
	"taap_project/helpers"
	"taap_project/models"

	"encoding/json"
	"net/http"
)

func AllDrillsController(w http.ResponseWriter, r *http.Request) {
	dbmap := helpers.GetGorpMap()
	var drillList []models.Drill
	if _, err := dbmap.Select(&drillList, "SELECT * FROM Drill"); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	js, err := json.Marshal(drillList)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.Write(js)
}
