package controllers

import (
	"html/template"
	"net/http"
)

func InputDrillController(w http.ResponseWriter, r *http.Request) {
	templates := template.Must(template.ParseFiles("views/input_drill.html"))

	if err := templates.ExecuteTemplate(w, "input_drill.html", nil); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

}
