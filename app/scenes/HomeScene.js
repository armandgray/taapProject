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

export default class HomeScene extends Component {
	constructor(props) {
		super(props)
	}
	postScene() {
		this.props.navigator.push({
			title: 'History',
			passProps: this.props
		})
	}
	render() {
		return(
			<Container theme={this.props.theme}>
				<Header style={styleHeader}>
					<Text style={{ color: '#FFFFFF', fontWeight: '900' }}>TAAP</Text>
		        	<Title style={{ color: '#FFFFFF' }}>
		        		Home
	        		</Title>
	        		<Button transparent
						onPress={this.props.toggleDrawer}>
						<Image style={{width: 30, height: 30}}
				        	source={require('../../images/ic_playlist_plus_white_48dp.png')} />
		        	</Button>
	        	</Header>
	        	<View>
	        		<Text>This is the Home Scene</Text>
        		</View>
    		</Container>
		)
	}
}

const styleHeader = {
	justifyContent: 'space-between', 
	alignItems: 'center', 
	backgroundColor: '#0000FF', 
}