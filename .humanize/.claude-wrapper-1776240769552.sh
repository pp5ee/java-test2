#!/bin/sh
cd '/app/workspaces/6fe6adce-110c-44ed-b8a1-fd3c7a778204' || exit 1
exec 'claude' '--dangerously-skip-permissions' '--print' '/humanize:start-rlcr-loop docs/plan.md --max 3 --yolo --codex-model minimax-m2.5:high --full-review-round 5 --track-plan-file --agent-teams --push-every-round'
