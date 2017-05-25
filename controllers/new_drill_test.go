package controllers_test

import (
	"taap_project/controllers"

	"net/http"
	"net/http/httptest"
	"testing"

	gmux "github.com/gorilla/mux"
)

func TestNewDrillController(t *testing.T) {
	mux := gmux.NewRouter()
	mux.HandleFunc("/", controllers.NewDrillController).Methods("GET")
	respRec := httptest.NewRecorder()
	req, err := http.NewRequest("GET", "/", nil)
	if err != nil {
		t.Fail("Creating 'GET /' request failed!")
	}

	mux.ServeHTTP(respRec, req)
	if respRec.Code != http.StatusOK {
		t.Fail("Server error: Returned ", respRec.Code, " instead of ", http.StatusOK)
	}
}
