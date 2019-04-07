package com.omila.todo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemLongClickListener {


    private lateinit var itemT: EditText
    private lateinit var btn: Button
    private lateinit var itemList: ListView

    private lateinit var  items: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    val FILENAME = "listinfo.dat"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemT   =findViewById<EditText>(R.id.item_edit_text)
        btn=findViewById<Button>(R.id.add_button)
        itemList=findViewById<ListView>(R.id.items_list)

        itemT.setBackgroundColor(49151);
        items= FileHelper.readData(this )
        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items)
        itemList.adapter=adapter


        btn.setOnClickListener(this)
        itemList.setOnItemLongClickListener(this);
       // itemList.setOnItemClickListener(this);
    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        items.removeAt(position)
        val fos = this.openFileOutput(FILENAME, Context.MODE_PRIVATE)//ovo je pomoglo da se obrisani item-i stvarno izbrisu i iz fajla, inace bi se vracali nakon restar aplikacije
        val oos = ObjectOutputStream(fos)
        oos.writeObject(items)
        oos.close()
        adapter.notifyDataSetChanged()
        Toast.makeText(this,"Item removed",Toast.LENGTH_SHORT).show()
        return true;
    }
    /*override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        btn.text= "Save"
        items.removeAt(position)
        adapter.notifyDataSetChanged()
        Toast.makeText(this,"Item removed",Toast.LENGTH_SHORT).show()
    }*/
    override fun onClick(v: View) {
        when(v.id){
            R.id.add_button-> {
                var itemEntered: String=itemT.text.toString()
                adapter.addAll(itemEntered)

                itemT.text=null
                FileHelper.writeData(items,this)

                Toast.makeText(this,"Item added",Toast.LENGTH_SHORT ).show()
            }
        }
    }
}
