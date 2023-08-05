import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymmanagement.R
import com.example.gymmanagement.adapters.MembersAdapter
import com.example.gymmanagement.models.MemberModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

 class allMembers : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var membersAdapter: MembersAdapter
    private lateinit var fabclass: FloatingActionButton
    private lateinit var calendar: Calendar
    private lateinit var dialog: View
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var memberList: MutableList<MemberModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_members, container, false)

        dialog = LayoutInflater.from(activity).inflate(R.layout.add_member_dialog, container, false)
        fabclass = view.findViewById(R.id.addMember)
        recyclerView = view.findViewById(R.id.members_recyclerview)
        ref = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        ref.keepSynced(true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        memberList = ArrayList()
        membersAdapter = MembersAdapter(requireContext(), memberList)
        recyclerView.adapter = membersAdapter

        fabclass.setOnClickListener {
            showDialog()
        }

        // Call this function to populate the memberList from Firebase using Coroutines
        getAllMembers()

        return view
    }

    private fun showDialog() {
        val mBuilder = AlertDialog.Builder(requireContext()).setView(dialog).show()
        val addBtn = mBuilder.findViewById<Button>(R.id.Add_member)
        val cancelBtn = mBuilder.findViewById<Button>(R.id.cancel_dialog)
        val memberName = mBuilder.findViewById<TextInputEditText>(R.id.member_name)
        val phoneNumber = mBuilder.findViewById<TextInputEditText>(R.id.member_phoneNo)

        addBtn?.setOnClickListener {
            val name = memberName?.text?.toString()?.trim()
            val phone = phoneNumber?.text?.toString()?.trim()

            if (name.isNullOrEmpty() || phone.isNullOrEmpty()) {
                return@setOnClickListener
            }

            val joiningDate = SimpleDateFormat("yyyy/MM/dd").format(Date()).toString()
            val key = ref.push().key.toString()

            val currentUser = auth.currentUser
            if (currentUser != null) {
                ref.child(currentUser.uid).child("All Members").child(key)
                    .setValue(MemberModel(name, phone, joiningDate, key))
                    .addOnSuccessListener {
                        mBuilder.dismiss()
                    }
                    .addOnFailureListener { exception ->
                        mBuilder.dismiss()
                    }
            } else {
                mBuilder.dismiss()
            }
        }

        cancelBtn?.setOnClickListener {
            mBuilder.dismiss()
        }
    }

    private fun getAllMembers() {
        ref.child(auth.currentUser!!.uid).child("All Members")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    memberList.clear()
                    for (memberSnapshot in dataSnapshot.children) {
                        val memberData = memberSnapshot.getValue(MemberModel::class.java)
                        memberData?.let { memberList.add(it) }
                    }
                    membersAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error appropriately.
                    // ...
                }
            })
    }

}
