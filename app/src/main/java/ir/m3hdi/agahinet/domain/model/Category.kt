package ir.m3hdi.agahinet.domain.model

data class Category(val id:Int,val title:String,val drawableId:Int){
    override fun toString(): String {
        return title
    }
}
