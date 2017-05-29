package models_test

import (
	. "taap_project/models"
	"testing"
)

func TestNewDrill(t *testing.T) {
	drill := Drill{"title", "category", 0}
	if drill.Title != "title" && drill.Category != "category" && drill.ImageId != 0 {
		t.Fail()
	}
}
