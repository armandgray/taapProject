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
import FooterNav from '../components/footer'

export default class HomeScene extends Component {
	constructor(props) {
		super(props)
	}
	postScene() {
		this.props.navigator.push({
			title: 'Profile',
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
	        		<Button transparent>
						<Image style={{width: 30, height: 30}}
				        	source={require('../../images/ic_playlist_plus_white_48dp.png')} />
		        	</Button>
	        	</Header>
	        	<View style={{ flex: 1 }}>
	        		<Text>This is the Home Scene</Text>
	        		<View style={{ flex: 11 }}/>
					<FooterNav style={{ justifyContent: 'flex-end' }} navigator={this.props.navigator} />
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