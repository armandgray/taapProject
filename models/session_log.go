package models

type SessionLog struct {
	LogId         int
	Date          int
	Goal          string
	ActiveWork    int
	RestTime      int
	SetsCompleted int
	RepsCompleted int
	SuccessRate   float64
	Drill         int
}
