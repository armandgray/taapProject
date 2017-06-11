package controllers

import (
	"fmt"
	"net/http"
	"taap_project/helpers"
	"taap_project/models"

	"strconv"
)

func NewDrillController(w http.ResponseWriter, r *http.Request) {
	var imageId int64
	imageId, err := strconv.ParseInt(r.FormValue("imageId"), 10, 64)
	if err != nil {
		imageId = 0
	}
	dbmap := helpers.GetGorpMap()
	drill := models.Drill{r.FormValue("title"), r.FormValue("category"), imageId}
	if err := dbmap.Insert(&drill); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	w.Write([]byte("Drill Added!"))
}
