import React, { Component } from 'react'
import { Text, View } from 'react-native'

export default class AppContainer extends Component {
	constructor(props) {
		super(props)
		this.state = {
			store: {},
			theme: null
		}
	}

	render() {
		return (
			<View>
				<Text>Hello World</Text>
			</View>
		)
	}
}