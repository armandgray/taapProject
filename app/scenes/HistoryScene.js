import React, { Component } from 'react'
import {
	Container,
	Header, 
	Content,
	Text,
	View,
	Button,
	Title
} from 'native-base'
import { Image } from 'react-native'

export default class HistoryScene extends Component {
	constructor(props) {
		super(props)
	}
	postScene() {
		this.props.navigator.push({
			title: 'Match',
			passProps: this.props
		})
	}
	render() {
		return(
			<Container theme={this.props.theme}>
				<Header style={{ justifyContent: 'space-between', alignItems: 'center', backgroundColor: '#0000FF' }}>
					<Button transparent
						onPress={this.props.toggleDrawer}>
						<Image style={{width: 30, height: 30}}
				        	source={require('../../images/ic_playlist_plus_white_48dp.png')} />
		        	</Button>
		        	<Title style={{ color: '#FFFFFF' }}>
		        		History
	        		</Title>
	        		<Button transparent
						onPress={this.postScene.bind(this)}>
						<Image style={{width: 30, height: 30}}
				        	source={require('../../images/ic_dots_vertical_white_48dp.png')} />
		        	</Button>
	        	</Header>
	        	<View>
	        		<Text>This is the History Scene</Text>
        		</View>
    		</Container>
		)
	}
}