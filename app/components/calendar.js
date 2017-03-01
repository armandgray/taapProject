import React, { Component } from 'react'
import { Text, View } from 'native-base'
import { Dimensions, StyleSheet } from 'react-native'

const CalendarPicker = require('react-native-calendar-picker')

export default class Calendar extends Component {
	constructor(props) {
		super(props)
    this.state = {
			date: new Date()
		}
	}

  onDateChange(date) {
    this.setState({ date: date });
  }

	render() {
		return (
      <View style={{ marginTop: 10 }}>

        <CalendarPicker
          selectedDate={this.state.date}
          onDateChange={this.onDateChange}
          screenWidth={Dimensions.get('window').width}
          selectedBackgroundColor={'#5ce600'} />

        <Text style={{ backgroundColor: 'rgba(0,0,0,0)', color: '#000' }}>
          Date: { this.state.date.toString() }
        </Text>
      </View>
		);
	}
}
