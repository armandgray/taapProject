import React, { Component } from 'react'
import {
	Container,
	Header, 
	Content,
	List,
	ListItem,
	Text,
	View,
	Button
} from 'native-base'

export default class DrawerMenu extends Component {
	constructor(props) {
		super(props)
	}
	render() {
		let list = [{
			title: 'Match',
			onPress: () => {
				this.props.navigator.replace({
					title: 'Match',
					passProps: this.props
				})
			}
		}, {
			title: "History",
			onPress: () => {
				this.props.navigator.replace({
					title: 'History',
					passProps: this.props
				})
			}
		}]
		return(
			<Container theme={this.props.theme}>
				<Header/>
				<View>
					 <List dataArray={list} renderRow={(item) => 
					 	<ListItem button onPress={item.onPress.bind(this)}>
					 		<Text>{item.title}</Text>
				 		</ListItem>
					 	} />
			 	</View>
		 	</Container>
		)
	}
}