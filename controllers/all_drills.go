package controllers

import (
	"taap_project/helpers"
	"taap_project/models"

	"net/http"
)

func AllDrillsController(w http.ResponseWriter, r *http.Request) {
	dbmap := helpers.GetGorpMap()
	var drillList []models.Drill
	if _, err := dbmap.Select(&drillList, "SELECT * FROM Drills"); err != nil {
		w.Write(err.Error())
		return
	}

	js, err := json.Marshal(userList)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.Write(js)
}
