package controllers

import (
	"fmt"
	"net/http"
	"taap_project/helpers"
	"taap_project/models"

	"strconv"
)

func NewDrillController(w http.ResponseWriter, r *http.Request) {
	submit := "submit=" + r.FormValue("submit")
	title := "title=" + r.FormValue("title")
	category := "category=" + r.FormValue("category")
	image := "imageId=" + r.FormValue("imageId")

	fmt.Println(submit)
	fmt.Println(title)
	fmt.Println(category)
	fmt.Println(image)

	if r.FormValue("submit") == "" {
		w.Write([]byte("Form Submission Invalid!"))
		return
	}

	var imageId int64
	imageId, err := strconv.ParseInt(r.FormValue("imageId"), 10, 64)
	if err != nil {
		imageId = 0
	}
	dbmap := helpers.GetGorpMap()
	drill := models.Drill{r.FormValue("title"), r.FormValue("category"), imageId}
	if err := dbmap.Insert(&drill); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	w.Write([]byte("Drill Added!"))
}
