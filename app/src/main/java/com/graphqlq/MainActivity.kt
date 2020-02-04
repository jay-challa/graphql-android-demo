package com.graphqlq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import android.os.Looper
import android.view.View
import android.widget.*
import com.graphqlq.MyApolloClient.Companion.apolloClient
import javax.annotation.Nonnull


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var name1: TextView
    lateinit var name2: TextView
    lateinit var disc1: TextView
    lateinit var disc2: TextView

    lateinit var nameET: EditText
    lateinit var discET: EditText
    lateinit var submitBtn: Button

    lateinit var userId: EditText
    lateinit var rel1: RelativeLayout
    lateinit var rel2: RelativeLayout
    lateinit var deleteButton : Button

    lateinit var type: String
    lateinit var id1: String
    lateinit var id2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        name1 = findViewById<TextView>(R.id.name1)
        name2 = findViewById<TextView>(R.id.name2)
        disc1 = findViewById<TextView>(R.id.disc1)
        disc2 = findViewById<TextView>(R.id.disc2)

        nameET = findViewById<EditText>(R.id.nameET)
        discET = findViewById<EditText>(R.id.discET)
        submitBtn = findViewById<Button>(R.id.submitBtn)

        userId = findViewById<EditText>(R.id.userId)
        rel1 = findViewById<RelativeLayout>(R.id.rl1)
        rel2 = findViewById<RelativeLayout>(R.id.rl2)
        deleteButton = findViewById<Button>(R.id.deleteBtn)

        submitBtn.setOnClickListener(this)
        deleteButton.setOnClickListener(this)
        rel1.setOnClickListener(this)
        rel2.setOnClickListener(this)

        getPost()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.submitBtn -> {
                if (nameET.text.toString().equals("")) {
                    Toast.makeText(this@MainActivity, "Name Required", Toast.LENGTH_SHORT).show()
                } else if (discET.text.toString().equals("")) {
                    Toast.makeText(this@MainActivity, "Discription required", Toast.LENGTH_SHORT).show()
                } else {
                    sendDataToServer(nameET.text.toString(), discET.text.toString())
                }

            }
            R.id.rl1 -> {
                userId.setText(id1)
            }
            R.id.rl2 -> {
                userId.setText(id2)
            }
            R.id.deleteBtn ->{
                if(userId.text.toString().equals("")){
                    Toast.makeText(this@MainActivity, "ID Required", Toast.LENGTH_SHORT).show()
                }else{
                    deleteData(userId.text.toString())
                }
            }
        }
    }

    fun deleteData(id: String) {
        apolloClient.mutate(DeletePostMutation.builder().id(id).build()).enqueue(object : ApolloCall.Callback<DeletePostMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                Toast.makeText(this@MainActivity, "Fail", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(response: Response<DeletePostMutation.Data>) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, response.data()!!.deletePost()!!.name()+" Deleted Successfully", Toast.LENGTH_SHORT).show()
                    getPost()
                    userId.setText("")
                }
            }
        })
    }

    fun sendDataToServer(name: String, disc: String) {
        apolloClient.mutate(CreatePostsMutation.builder().name(name).discriptions(disc).build()).enqueue(object : ApolloCall.Callback<CreatePostsMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                Toast.makeText(this@MainActivity, "Fail", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(response: Response<CreatePostsMutation.Data>) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Created Success", Toast.LENGTH_SHORT).show()
                    getPost()
                    nameET.setText("")
                    discET.setText("")

                }
            }
        })
    }

    fun getPost() {

        /* MyApolloClient.getApolloClients()!!.query(AllNameQuery.builder().build())
                 .enqueue(object : ApolloCall.Callback<AllNameQuery.Data>(){
                     override fun onFailure(e: ApolloException) {
                         Toast.makeText(this@MainActivity, "Fail", Toast.LENGTH_SHORT).show()
                     }

                     override fun onResponse(response: Response<AllNameQuery.Data>) {
                         runOnUiThread {
                             Toast.makeText(this@MainActivity, "" +
                                     response.data()!!.allPosts().get(0).name() + "\n" +
                                     response.data()!!.allPosts().get(1).name() ,
                                     Toast.LENGTH_SHORT).show()
                             name1.text = response.data()!!.allPosts().get(0).name()
                             name2.text = response.data()!!.allPosts().get(1).name()

                         }
                     }

                 }) */

        /*apolloClient
                .query(AllNameQuery.builder().build())
                .enqueue(object : ApolloCall.Callback<AllNameQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Toast.makeText(this@MainActivity, "Fail", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(response: Response<AllNameQuery.Data>) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "" +
                                    response.data()!!.allPosts().get(0).name() + "\n" +
                                    response.data()!!.allPosts().get(1).name() ,
                                    Toast.LENGTH_SHORT).show()
                            name1.text = response.data()!!.allPosts().get(0).name()
                            name2.text = response.data()!!.allPosts().get(1).name()


                        }
                    }
                });*/

        /* MyApolloClient.getApolloClients()!!.query(AllDiscriptionQuery.builder().build())
                 .enqueue(object : ApolloCall.Callback<AllDiscriptionQuery.Data>(){
                     override fun onFailure(e: ApolloException) {
                         Toast.makeText(this@MainActivity, "Fail", Toast.LENGTH_SHORT).show()
                     }

                     override fun onResponse(response: Response<AllDiscriptionQuery.Data>) {
                         runOnUiThread {
                             Toast.makeText(this@MainActivity, "" +
                                     response.data()!!.allPosts().get(0).discriptions() + "\n" +
                                     response.data()!!.allPosts().get(1).discriptions() ,
                                     Toast.LENGTH_SHORT).show()
                             disc1.text = response.data()!!.allPosts().get(0).discriptions()
                             disc2.text = response.data()!!.allPosts().get(1).discriptions()

                         }
                     }

                 })*/

        apolloClient
                .query(AllPostQuery.builder().build())
                .enqueue(object : ApolloCall.Callback<AllPostQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Toast.makeText(this@MainActivity, "Fail", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(response: Response<AllPostQuery.Data>) {
                        runOnUiThread {
                            id1 = response.data()!!.allPosts().get(response.data()!!.allPosts().size-1).id()
                            id2 = response.data()!!.allPosts().get(response.data()!!.allPosts().size-2).id()

                            name1.text = response.data()!!.allPosts().get(response.data()!!.allPosts().size-1).name()
                            name2.text = response.data()!!.allPosts().get(response.data()!!.allPosts().size-2).name()

                            disc1.text = response.data()!!.allPosts().get(response.data()!!.allPosts().size-1).discriptions()
                            disc2.text = response.data()!!.allPosts().get(response.data()!!.allPosts().size-2).discriptions()
                        }
                    }
                });

    }
}
