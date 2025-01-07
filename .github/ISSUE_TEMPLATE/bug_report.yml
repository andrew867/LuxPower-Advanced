---
name: "Bug Report"
description: "Report an issue or bug."
title: "[BUG] Brief description of the issue"
labels:
  - bug
assignees: []
# Required for new GitHub Issue Forms
version: 2

body:
  - type: input
    id: version
    attributes:
      label: "Version Information"
      description: "Enter the version of the integration.."
      placeholder: "Enter version here"
    validations:
      required: true

  - type: textarea
    id: images_videos
    attributes:
      label: "Media Attachments"
      description: "Provide links to any images or videos showing the issue."
      placeholder: "Paste links to images or videos here"
    validations:
      required: true

  - type: textarea
    id: log_output
    attributes:
      label: "Log File Output"
      description: "Please add the log file output here:"
      placeholder: "Paste log output here"
      render: shell
    validations:
      required: false

  - type: textarea
    id: detailed_description
    attributes:
      label: "Detailed Description"
      description: "Provide a detailed description of the issue, steps to reproduce, and expected behavior."
      placeholder: "Describe the issue in detail"
    validations:
      required: true

  - type: checkboxes
    id: issue_check
    attributes:
      label: "Issue Confirmation"
      description: "Check the box to confirm you have verified this bug isn't already reported."
      options:
        - label: "I have searched the Issues tab and confirmed this issue does not already exist."
          required: true
---
