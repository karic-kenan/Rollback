package io.aethibo.rollback.features.login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.aethibo.rollback.R
import io.aethibo.rollback.domain.mapped.UserItem

class LoginAdapter : ListAdapter<UserItem, LoginAdapter.LoginViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return LoginViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoginViewHolder, position: Int) {
        holder.onBind(getItem(position) ?: return)
    }

    inner class LoginViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(user: UserItem) = with(itemView) {

            /**
             * Get views
             */
            val name = findViewById<TextView>(R.id.tvUserItemName)
            val city = findViewById<TextView>(R.id.tvUserItemAddressCity)
            val street = findViewById<TextView>(R.id.tvUserItemAddressStreet)

            /**
             * Init views
             */
            name.text = "${user.firstName} ${user.lastName}"
            city.text = user.city
            street.text = "${user.street} - ${user.zipCode}"

            setOnClickListener {
                onUserClickListener?.let { click -> click(user) }
            }
        }
    }

    /**
     * Click listeners
     */
    private var onUserClickListener: ((UserItem) -> Unit)? = null

    fun onUserEventClickListener(listener: (UserItem) -> Unit) {
        onUserClickListener = listener
    }
}