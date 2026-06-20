# frameworks_base_derpfest

Custom fork of [DerpFest-AOSP/frameworks_base](https://github.com/DerpFest-AOSP/frameworks_base) with device-specific patches for Xiaomi Redmi 4X (santoni).

## Branches

| Branch | Description |
|:-------|:------------|
| `derp-13-dev` | **Custom branch** — DerpFest 13 + santoni patches (default) |
| `13` | Upstream DerpFest 13 (unmodified) |
| `13-asb` | Upstream DerpFest 13 ASB (unmodified) |
| Other (`11`, `12.1`, `14`, `15`, etc.) | Upstream DerpFest branches (unmodified) |

## Custom Patches (derp-13-dev)

Branch `derp-13-dev` is based on upstream `13` with these additions:

| Commit | Description |
|:-------|:------------|
| `2851d711` | **Spectrum QS tile** — Quick Settings tile for kernel profile switching (Balance/Performance/Battery/Gaming) |
| `0faf8796` | **SystemUIGoogle privapp permissions** — Add missing ACCESS_CONTEXT_HUB, SET_WALLPAPER_COMPONENT, REGISTER_STATS_PULL_ATOM to allowlist |
| `a3d667c5` | **Reticker crash fix** — Add isAttachedToWindow() guard before createCircularReveal() to prevent SystemUI crash on detached view |

## Usage

This repo is used as a replacement for `frameworks/base` when building DerpFest 13 for santoni:

```bash
# After repo sync, replace frameworks/base with this fork
cd frameworks/base
git remote add ziachi https://github.com/ziachi/frameworks_base_derpfest.git
git fetch ziachi derp-13-dev
git checkout ziachi/derp-13-dev
```

## Related Repositories

| Component | Repository | Branch |
|:----------|:-----------|:-------|
| **Device Tree** | [ziachi/device_xiaomi_santoni_derpfest](https://github.com/ziachi/device_xiaomi_santoni_derpfest) | `derp-13-dev` |
| **Kernel** | [ziachi/kernel_xiaomi_msm8937_derpfest](https://github.com/ziachi/kernel_xiaomi_msm8937_derpfest) | `derp-13-dev` |
| **Vendor** | [ziachi/vendor_xiaomi_santoni_derpfest](https://github.com/ziachi/vendor_xiaomi_santoni_derpfest) | `derp-13-dev` |
