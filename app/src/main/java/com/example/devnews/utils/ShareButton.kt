package com.example.devnews.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun ShareButton( title: String, url: String?, onShareClick: (String) -> Unit) {
    IconButton(onClick = {
        url?.let { onShareClick(it) }
    }) {
        Icon(
            imageVector = Icons.Filled.Share, contentDescription = "Share"
        )
    }
}

//[Unit]
//Description=Cloudflare Tunnel
//After=network.target
//
//[Service]
//Type=simple
//ExecStart=/home/ubuntu/DevNews-backend/venv/bin/cloudflared tunnel --url http://localhost:80
//Restart=always
//User=ubuntu
//WorkingDirectory=/home/ubuntu/DevNews-backend
//
//[Install]
//WantedBy=multi-user.target