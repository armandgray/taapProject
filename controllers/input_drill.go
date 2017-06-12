package controllers

import (
	"html/template"
	"net/http"
)

func InputDrillController(w http.ResponseWriter, r *http.Request) {
	if r.FormValue("submit") != "" {
		http.Redirect(w, r, "/taap/api/drills/new", http.StatusFound)
		return
	}

	templates := template.Must(template.ParseFiles("views/input_drill.html"))
	if err := templates.ExecuteTemplate(w, "input_drill.html", nil); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

}
