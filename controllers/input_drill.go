package controllers

import (
	"html/template"
	"net/http"
)

func InputDrillController(w http.ResponseWriter, r *http.Request) {
	if r.FormValue("submit") != "" {
		base := "/taap/api/drills/new"
		submit := "submit=" + r.FormValue("submit")
		title := "&title=" + r.FormValue("title")
		category := "&category=" + r.FormValue("category")
		imageId := "&imageId=" + r.FormValue("imageId")
		route := base + "?" + submit + title + category + imageId

		http.Redirect(w, r, route, http.StatusFound)
		return
	}

	templates := template.Must(template.ParseFiles("views/input_drill.html"))
	if err := templates.ExecuteTemplate(w, "input_drill.html", nil); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

}
