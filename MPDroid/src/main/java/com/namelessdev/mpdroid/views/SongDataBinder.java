/*
 * Copyright (C) 2010-2015 The MPDroid Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.namelessdev.mpdroid.views;

import com.anpmech.mpd.item.Item;
import com.anpmech.mpd.item.Music;
import com.namelessdev.mpdroid.R;
import com.namelessdev.mpdroid.adapters.ArrayDataBinder;
import com.namelessdev.mpdroid.views.holders.AbstractViewHolder;
import com.namelessdev.mpdroid.views.holders.SongViewHolder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class SongDataBinder<T extends Item<T>> implements ArrayDataBinder<T> {

    private final boolean mShowArtist;

    public SongDataBinder() {
        this(true);
    }

    public SongDataBinder(final boolean showArtist) {
        super();
        mShowArtist = showArtist;
    }

    @Override
    public AbstractViewHolder findInnerViews(final View targetView) {
        return new SongViewHolder(targetView);
    }

    @Override
    @LayoutRes
    public int getLayoutId() {
        return R.layout.song_list_item;
    }

    @Override
    public boolean isEnabled(final int position, final List<T> items, final Object item) {
        return true;
    }

    @Override
    public void onDataBind(final Context context, final View targetView,
            final AbstractViewHolder viewHolder, final List<T> items, final Object item,
            final int position) {
        final SongViewHolder holder = (SongViewHolder) viewHolder;
        final Music song = (Music) item;
        final StringBuilder track = new StringBuilder(3);
        int trackNumber = song.getTrack();

        if (trackNumber < 0) {
            trackNumber = 0;
        }

        if (trackNumber < 10) {
            track.append(0);
        }
        track.append(trackNumber);

        holder.getTrackTitle().setText(song.getTitle());
        holder.getTrackNumber().setText(track);
        holder.getTrackDuration().setText(song.getFormattedTime());

        if (mShowArtist) {
            holder.getTrackArtist().setText(song.getArtistName());
        }

        if (song.getComments().length() > 0) {
            holder.getComment().setVisibility(View.VISIBLE);
            holder.getComment().setTag(song.getComments());
        } else {
            holder.getComment().setVisibility(View.GONE);
        }
    }

    @Override
    public View onLayoutInflation(final Context context, final View targetView,
            final List<T> items) {
        targetView.findViewById(R.id.show_comments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(context, (String)v.getTag(), Toast.LENGTH_LONG).show();
            }
        });
        return BaseDataBinder.setViewVisible(targetView, R.id.track_artist, mShowArtist);
    }

}
