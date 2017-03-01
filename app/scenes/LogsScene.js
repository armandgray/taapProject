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
import Calendar from '../components/calendar'

export default class LogsScene extends Component {
	constructor(props) {
		super(props)
	}
	postScene() {
		this.props.navigator.push({
			title: 'Home',
			passProps: this.props
		})
	}
	render() {
		return(
			<Container theme={this.props.theme}>
				<Header style={styleHeader}>
					<Text style={{ color: '#FFFFFF', fontWeight: '900' }}>TAAP</Text>
		        	<Title style={{ color: '#FFFFFF' }}>
		        		Logs
	        		</Title>
	        		<Button transparent>
						<Image style={{width: 30, height: 30}}
				        	source={require('../../images/ic_playlist_plus_white_48dp.png')} />
		        	</Button>
	        	</Header>
	        	<View style={{ flex: 1 }}>
	        		<Calendar style={{ flex: 1 }}/>
							<View style={{ flex: 3 }}/>
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
