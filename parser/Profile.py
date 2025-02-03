from dataclasses import dataclass
from datetime import datetime


@dataclass
class Profile():
    profileId: int
    consultantId: str
    consultantName: str
    profileRef: str
    creationDate: datetime
    role: str
    profileState: str
    profileName: str
    profileJobTitle: str
    profileExecutiveSummary: str
    profileEmail: str
    profileMobility: int
    profileLanguages: []
    profileMethodologies: []
    profileCertifications: []
    profileIndustrySectors: []
    profileTechnicalExpertise: []
    profileFunctionalExpertise: []
    profileProjects: []
    profileExperience: []




