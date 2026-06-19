/*
 * Copyright (C) 2024 DerpFest
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

package com.android.systemui.qs.tiles;

import static com.android.internal.logging.MetricsLogger.VIEW_UNKNOWN;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.service.quicksettings.Tile;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.dagger.qualifiers.Background;
import com.android.systemui.dagger.qualifiers.Main;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile.State;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;

import javax.inject.Inject;

/** Quick settings tile: Spectrum kernel profile switcher **/
public class SpectrumTile extends QSTileImpl<State> {

    public static final String TILE_SPEC = "spectrum";

    private static final String SPECTRUM_PROFILE_PROP = "persist.spectrum.profile";
    private static final String SPECTRUM_KERNEL_PROP = "persist.spectrum.kernel";

    private static final int PROFILE_BALANCE = 0;
    private static final int PROFILE_PERFORMANCE = 1;
    private static final int PROFILE_BATTERY = 2;
    private static final int PROFILE_GAMING = 3;
    private static final int PROFILE_COUNT = 4;

    private final Icon mIconBalance = ResourceIcon.get(R.drawable.ic_qs_spectrum_balance);
    private final Icon mIconPerformance = ResourceIcon.get(R.drawable.ic_qs_spectrum_performance);
    private final Icon mIconBattery = ResourceIcon.get(R.drawable.ic_qs_spectrum_battery);
    private final Icon mIconGaming = ResourceIcon.get(R.drawable.ic_qs_spectrum_gaming);

    @Inject
    public SpectrumTile(
            QSHost host,
            @Background Looper backgroundLooper,
            @Main Handler mainHandler,
            FalsingManager falsingManager,
            MetricsLogger metricsLogger,
            StatusBarStateController statusBarStateController,
            ActivityStarter activityStarter,
            QSLogger qsLogger
    ) {
        super(host, backgroundLooper, mainHandler, falsingManager, metricsLogger,
                statusBarStateController, activityStarter, qsLogger);
    }

    @Override
    public State newTileState() {
        State state = new State();
        state.handlesLongClick = false;
        return state;
    }

    @Override
    protected void handleClick(@Nullable View view) {
        int current = getProfile();
        int next = (current + 1) % PROFILE_COUNT;
        SystemProperties.set(SPECTRUM_PROFILE_PROP, String.valueOf(next));
        refreshState();
    }

    @Override
    public Intent getLongClickIntent() {
        return null;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_spectrum_label);
    }

    @Override
    protected void handleUpdateState(State state, Object arg) {
        int profile = getProfile();

        switch (profile) {
            case PROFILE_PERFORMANCE:
                state.label = mContext.getString(R.string.quick_settings_spectrum_performance);
                state.icon = mIconPerformance;
                state.state = Tile.STATE_ACTIVE;
                break;
            case PROFILE_BATTERY:
                state.label = mContext.getString(R.string.quick_settings_spectrum_battery);
                state.icon = mIconBattery;
                state.state = Tile.STATE_ACTIVE;
                break;
            case PROFILE_GAMING:
                state.label = mContext.getString(R.string.quick_settings_spectrum_gaming);
                state.icon = mIconGaming;
                state.state = Tile.STATE_ACTIVE;
                break;
            case PROFILE_BALANCE:
            default:
                state.label = mContext.getString(R.string.quick_settings_spectrum_balance);
                state.icon = mIconBalance;
                state.state = Tile.STATE_ACTIVE;
                break;
        }

        state.contentDescription = state.label;
    }

    @Override
    public int getMetricsCategory() {
        return VIEW_UNKNOWN;
    }

    @Override
    public void handleSetListening(boolean listening) {
        // no-op
    }

    private int getProfile() {
        try {
            return Integer.parseInt(SystemProperties.get(SPECTRUM_PROFILE_PROP, "0"));
        } catch (NumberFormatException e) {
            return PROFILE_BALANCE;
        }
    }
}
