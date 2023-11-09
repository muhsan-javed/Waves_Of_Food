package com.muhsanjaved.wavesoffood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.databinding.FragmentProfileBinding
import com.muhsanjaved.wavesoffood.models.UserModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.apply {
            profileName.isEnabled = false
            profileEmail.isEnabled = false
            profileAddress.isEnabled= false
            profilePhoneNumber.isEnabled = false

            profileEditButton.setOnClickListener {
                    profileName.isEnabled = !profileName.isEnabled
                    profileEmail.isEnabled = !profileEmail.isEnabled
                    profileAddress.isEnabled = !profileAddress.isEnabled
                    profilePhoneNumber.isEnabled = !profilePhoneNumber.isEnabled
            }
        }

        setUserData()
        // Svae User Data
        binding.saveUserInformationButton.setOnClickListener {
            val name = binding.profileName.text.toString().trim()
            val email = binding.profileEmail.text.toString().trim()
            val address = binding.profileAddress.text.toString().trim()
            val phone = binding.profilePhoneNumber.text.toString().trim()

            updateUserData(name,email,address,phone)
        }

        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId != null){

            val userReference = database.getReference("user").child(userId)

            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "address" to address,
                "phone" to phone,
            )

            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"Profile Update Successfully 😊", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Profile Update Failed 😒", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userReferencer = database.getReference("user").child(userId)

            userReferencer.addListenerForSingleValueEvent(object :ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null){
                            binding.profileName.setText(userProfile.name)
                            binding.profileEmail.setText(userProfile.email)
                            binding.profileAddress.setText(userProfile.address)
                            binding.profilePhoneNumber.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed",Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    companion object {

    }
}