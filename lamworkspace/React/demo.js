class ShoppingList extends React.Component{
    render(){
        return (
            <div className="shopping-List">
                <h1>Shopping List for {this.props.name}</h1>
                <ul>
                    <li>Instagram</li>
                    <li>What's App</li>
                    <li>Oculus</li>   
                </ul>
            </div>
        );
    }
}